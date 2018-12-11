<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>HomePage</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <#--<script type="text/javascript" src="homePage.js"/>-->

    <script type="text/javascript">
        var error = "${err}";
        var mess = "${message}";
        var alert = "${alert}";
        $(function (){
            if(error!=""){
                $("#error").show();
            }else $("#error").hide();

            if(mess!=""){
                $("#message").show();
            }else $("#message").hide();

            if(alert!=""){
                $("#alert").show();
                $("#arrow").show();
            }else{
                $("#alert").hide();
                $("#arrow").hide();
            }
        });

        function validate(inp) {
            inp.value = inp.value.replace(/[^\d.]*/g, '')
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
        });

    </script>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" <#--id="btn-tooltip" title="Exit to Login page!" href="/startPage.html"-->>TestProject</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" <#--href="/homePage"-->>Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/storyPage">Story</a>
                </li>
                <li>
                    <form action="/logout" method="post">
                        <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button class="btn btn-outline-success my-2 my-sm-0 ml-3" type="submit">Exit</button>
                    </form>
                </li>
            </ul>
            <ul class="navbar-nav">
                <li>
                    <div id="arrow" style="display: none">
                        <img src="https://avatars.mds.yandex.net/get-pdb/750997/e63e1bf3-0292-49a9-897e-c53048f94b05/s1200" width="43" height="30" class="d-inline-block align-top mr-3" alt="">
                    </div>
                </li>
                <li>
                    <div>
                        <button class="btn btn-outline-success my-2 my-sm-0 mr-3" data-toggle="modal" data-target="#info">Change information</button>
                    </div>
                    <div id="info" class="modal fade" tabindex="-1" role="dialog">
                        <div class="modal-dialog modal-sm">
                            <form class="modal-content" align="right" method="post" action="/change">
                                <div class="modal-header">
                                    <button type="button" class="close ml-0" data-dismiss="modal" aria-hidden="true">×</button>
                                    <h3 class="modal-title">New information</h3>
                                </div>
                                <div class="mr-1"><label>User Name * : <input id="userNameNew" type="text" name="userNameNew" placeholder="User Name" autocomplete="off"/></label></div>
                                <div class="mr-1"><label>Login * : <input id="loginNew" type="text" name="loginNew" placeholder="Login" autocomplete="off"/></label></div>
                                <div class="mr-1"><label>Password * : <input id="passwordNew" type="password" name="passwordNew" placeholder="Password" autocomplete="off"/></label></div>
                                <div class="mr-1"><label>Admin code : <input type="text" id="codeNew" name="codeNew" placeholder="Admin code" autocomplete="off"/></label></div>
                                <a class="mr-3" style="color: orange; font-size: x-small" >*All fields * are required!</a>
                                <a class="mr-3" style="color: red; font-size: x-small" >*If you change login, your transactions story will be lost!</a>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                    <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" id="login" name="login" value="${login}"/>
                                    <input type="hidden" id="role" name="role" value="${role}"/>
                                    <button type="submit" class="btn btn-outline-success" >Save</button>
                                </div>
                            </form>
                            <div id="alert" class="navbar alert alert-info" align="left" style="display: none">
                                <span style="font-size: small"><strong>Be attentive!</strong> ${alert}</span>
                            </div>
                        </div>
                    </div>
                <li>
                    <a class="nav-link disabled text-success">${role} : ${userName}!</a>
                </li>
            </ul>
        </div>
    </nav>
    <!------------------------------------------------------------------------------------>

    <div id="homeform" class="ml-5 mr-5 mt-3">
        <table class="table table-reflow" border="2">
            <tbody>
            <tr class="table-primary">
                <td>Name</td>
                <td>${userName}</td>
            </tr>
            <tr class="table-secondary">
                <td>Login</td>
                <td>${login}</td>
            </tr>
            <tr class="table-success">
                <td>Blabance ETH</td>
                <td>${balanceEth}</td>
            </tr>
            <tr class="table-warning">
                <td>Balance USD</td>
                <td>${balanceUsd}</td>
            </tr>
            </tbody>
        </table>
    </div>

    <form id="add" class="navbar ml-5" style="display: block" method="post" action="/increase">
        <span>Increase USD balance : </span>
        <span><input id="sum" name="sum" type="text" onkeyup="validate(this)" size="7" maxlength="6" autocomplete="off" value="0"></span>
        <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" id="login" name="login" value="${login}"/>
        <input type="hidden" id="role" name="role" value="${role}"/>
        <span><input type="submit" value="Add"></span>
    </form>
    <form id="donation" class="navbar ml-5" style="display: block" method="post" action="/donate">
        <span>Donation : </span>
        <span><input id="donateUsd" name="donateUsd" type="text" onkeyup="validate(this)" size="7" maxlength="6" autocomplete="off" value="0"> USD ; </span>
        <span><input id="donateEth" name="donateEth" type="text" onkeyup="validate(this)" size="7" maxlength="6" autocomplete="off" value="0"> ETH </span>
        <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" id="login" name="login" value="${login}"/>
        <input type="hidden" id="role" name="role" value="${role}"/>
        <span><input type="submit" value="Donate"></span>
    </form>
    <form id="trans" class="navbar row ml-5" style="display: block" method="post" action="/transfer">
        <select class="col-xs" id="transoper" name="transoper" onchange="changeMoneyOper(value)">
            <option value="1">Buy</option>
            <option value="2">Sell</option>
        </select>
        <select class="col-xs" id="transtype" name="transtype" onchange="changeMoneyType(value)">
            <option value="1">USD</option>
            <option value="2">ETH</option>
        </select>
        <span><input id="transval" name="transval" type="text" onkeyup="validate(this)" size="7" maxlength="5" oninput="convertSum(value)" value="0" autocomplete="off"></span>
        <span> -> </span>
        <select class="retransoper col-xs-2" id="retransoper" name="retransoper" disabled>
            <option value="1">Sell</option>
            <option value="2">Buy</option>
        </select>
        <select class="retranstype col-xs-2" id="retranstype" name="retranstype" disabled>
            <option value="1">ETH</option>
            <option value="2">USD</option>
        </select>
        <span><input type="text" class="convertValue" name="convertValue" onkeyup="validate(this)" size="10" value="0" readonly></span>
        <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" id="login" name="login" value="${login}"/>
        <input type="hidden" id="role" name="role" value="${role}"/>
        <span><input type="submit" value="Transfer"></span>
    </form>
    <form id="first" class="navbar row ml-5" style="display:block" method="post" action="/first">
        <span><label> Usd * : <input id="usd" name="usd" class="ml-2" type="text" onkeyup="validate(this)" size="7" maxlength="5" placeholder="Enter USD" autocomplete="off" title="Max value = 99999" value="0"/> </label></span>
        <span><label> Eth * : <input id="eth" name="eth" class="ml-2" type="text" onkeyup="validate(this)" size="7" maxlength="5" placeholder="Enter ETH" autocomplete="off" title="Max value = 2.000" value="0"/> </label></span>
        <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" id="role" name="role" value="${role}"/>
        <input type="hidden" id="login" name="login" value="${login}"/>
        <span><input type="submit" value="Create start capital"/><br></span>
    </form>
    <div id="error" class="navbar alert alert-danger ml-3 mr-3" align="left" style="display: none">
        <span><strong>Oh snap!</strong> ${err}</span>
    </div>
    <div id="message" class="navbar alert alert-warning ml-3 mr-3" align="left" style="display: none">
        <span><strong>Warning!</strong> ${message}</span>
    </div>
</body>
</html>