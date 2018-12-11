
/*$(function (){
    var error = "${err}";
    var mess = "${message}";
    if(error!=""){
        $("#error").show();
    }else $("#error").hide();

    if(mess!=""){
        $("#message").show();
    }else $("#message").hide();
});

function validate(inp) {
    inp.value = inp.value.replace(/[^\d.]*!/g, '')
        .replace(/([.])[.]+/g, '$1')
        .replace(/^[^\d]*(\d+([.]\d{0,5})?).*$/g, '$1');
}

function changeMoneyOper(res) {
    $('select[name=retransoper]').val(res);
    $('.retransoper').retransoper('refresh');
}

function changeMoneyType(res) {
    $('select[name=retranstype]').val(res);
    $('.retranstype').retranstype('refresh');
}

function convertSum(res){
    var t=1;
    if($('select[name=transoper]').val()==1){
        t = t*(-1);
    }
    if($('select[name=transtype]').val()==1){
        t = t/(5432.1);
    }
    if($('select[name=transtype]').val()==2){
        t = t*(5432.1);
    }

    $('input[name=convertValue]').val(parseFloat(res*t).toFixed(5));
    $('.convertValue').convertValue('refresh');
}

$('#usd').tooltip();
$('#eth').tooltip();

$(function (){
    var balanceUsd = "${balanceUsd}";
    var balanceEth = "${balanceEth}";
    if(balanceEth=="" && balanceUsd==""){
        $('#add').hide();
        $('#donation').hide();
        $('#trans').hide();
    }else{
        $('#first').hide();
    }
});*/
