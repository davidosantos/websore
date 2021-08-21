
$(document).ready(function(){
    $("#alertMessage").hide();
});




function validate() {  
var result = "";	
result += validateName(); 	
result += validateEmail();
result += validateDocument();
result += validatePassword();
result += validateTerms();


if (result == "") return true;

$("#alertMessage").html(result);
$("#alertMessage").show();
return false;	
}

function validateName() {
var name = document.getElementsByName("name")[0].value;
if (name.length <= 3)
    return "O nome deve ter um mínimo de 3 caracteres.</br>";	
return "";
}

function validateDocument() {
var name = document.getElementsByName("document")[0].value;
if (name.length <= 10)
    return "CPF invalido</br>";	
return "";
}

function validatePassword() {
var password = valueOf("password");
var retype = valueOf("retype_password");

if (password.length <= 6) 
    return "A senha deve ter um mínimo de 6 caracteres.</br>";

if (password != retype) 
    return "As senhas informadas não conferem.</br>";	
return "";
}

function validateEmail() {
var email = valueOf("email");

if (email.indexOf('@') == -1) 
    return "O Email informado não é válido.</br>";
return "";
}

function validateTerms() {
var terms = document.getElementsByName("terms")[0];
if (!terms.checked)
    return "Por favor leia e aceite os termos";	
return "";
}

function valueOf(name) {
return document.getElementsByName(name)[0].value;
}