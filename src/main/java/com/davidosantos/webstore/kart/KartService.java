package com.davidosantos.webstore.kart;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KartService {

    @Autowired
    KartRepository kartRepository;
    
    public Kart createKart(){

        Kart kart = new Kart();

        return kartRepository.save(kart);
    }

    public Kart save(Kart kart){
        recalculateKart(kart);
        return kartRepository.save(kart);
    }

    public Kart getKart(String cartId){

        return kartRepository.findByIdAndStatus(cartId,"active").stream().findFirst().get();
    }

    public Kart getKartById(String kartId){

        return kartRepository.findById(kartId).get();
    }

    public int countKart(String cartId){
        return kartRepository.countByIdAndStatus(cartId, "active");
    }

    public void cancelItem(String kartId, int itemIndex){
        Kart kart = getKartById(kartId);
        kart.getItems().get(itemIndex).setStatus("canceled");
        save(kart);
        
    }

    public void reactivateItem(String kartId, int itemIndex){
        Kart kart = getKartById(kartId);
        kart.getItems().get(itemIndex).setStatus("active");
        save(kart);
        
    }

    public void alterQuantity(String kartId, int itemIndex, int quantity){
        Kart kart = getKartById(kartId);
        kart.getItems().get(itemIndex).setQuantity(quantity);
        save(kart);
    }

    public void recalculateKart(Kart kart){

        double sum =0;
        double discountSum =0;
        double freightSum =0;
        double productsSum =0;

        for (KartItem item : kart.items ){
            if(item.status.equals("active")){
                sum +=
                (item.getQuantity() * item.getProduct().getPrice().doubleValue())
                - (item.getQuantity() * item.getProduct().getDiscount().doubleValue())
                + (item.getQuantity() * item.getProduct().getFreight().doubleValue());
            }
        }
        kart.setTotalAmount(new BigDecimal(sum).setScale(2, RoundingMode.HALF_EVEN));

        for (KartItem item : kart.items ){
            if(item.status.equals("active")){
                discountSum += item.getQuantity() * item.getProduct().getDiscount().doubleValue();
            }
        }
        kart.setTotalDiscountAmount(new BigDecimal(discountSum).setScale(2, RoundingMode.HALF_EVEN));

        for (KartItem item : kart.items ){
            if(item.status.equals("active")){
                freightSum += item.getQuantity() * item.getProduct().getFreight().doubleValue();
            }
        }
        kart.setTotalFreightAmount(new BigDecimal(freightSum).setScale(2, RoundingMode.HALF_EVEN));

        for (KartItem item : kart.items ){
            if(item.status.equals("active")){
                productsSum += item.getQuantity() * item.getProduct().getPrice().doubleValue();
            }
        }
        kart.setTotalProductsAmount(new BigDecimal(productsSum).setScale(2, RoundingMode.HALF_EVEN));

    }

}
