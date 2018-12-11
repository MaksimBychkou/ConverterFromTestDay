<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

    <script type="text/javascript">
        var error = "${err}";
        $(function (){
            if(error!=""){
                $("#error").show();
                document.getElementById('login').style.borderColor='#ff0c02';
                document.getElementById('password').style.borderColor='#ff0c02';
            }else $("#error").hide();
        });
    </script>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand">TestProject</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-success" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Do you want to start?
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="btn dropdown-item" id="regmenu" href="/registration">Registration</a>
                    </div>
                </li>
            </ul>
            <ul class="navbar-nav">
                <li>
                    <a class="nav-link disabled text-success">Welcome, Guest!</a>
                </li>
            </ul>
        </div>
    </nav>
    <!------------------------------------------------------------------------------------>

    <form id="logform" class="navbar ml-5 form-5 col-md-3" style="display:block" align="right" method="post" action="/login">
        <div><label> Login * : <input id="login" type="text" name="username" placeholder="Login" autocomplete="off"/> </label></div>
        <div><label> Password * : <input id="password" type="password" name="password" placeholder="Password" autocomplete="off"/> </label></div>
        <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div><input type="submit" value="Sign In"/></div>
    </form>
    <div id="error" class="navbar alert alert-danger ml-3 mr-3" align="left" style="display: none">
        <span><strong>Oh snap!</strong> ${err}</span>
    </div>
</body>
<#--

добовить в application.properties
fisrt: spring.jpa.hibernate.ddl-auto=create-drop
last: logging.level.root=DEBUG

-->
</html>