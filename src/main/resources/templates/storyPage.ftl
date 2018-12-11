<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>StoryPage</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

    <script type="text/javascript">
        var role = "${role}";
        $(function (){
            if(role=="admin"){
                $('#admin').show();
                $('#usrList').show();
            }
        });
    </script>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" <#--id="btn-tooltip" title="Exit to Login page!" href=""-->>TestProject</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/homePage">Home</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" <#--href="/storyPage.html"-->>Story</a>
                </li>
                <li>
                    <form action="/logout" method="post">
                        <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button class="btn btn-outline-success my-2 my-sm-0 ml-3" type="submit">Exit</button>
                    </form>
                </li>
                <div class="btn-group ml-3">
                    <a class="btn dropdown-toggle" data-toggle="dropdown">
                        Filter by operation<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <a class="btn dropdown-item" id="increase" href="?filter=increase">Increase</a>
                        <a class="btn dropdown-item" id="donation" href="?filter=donation">Donation</a>
                        <a class="btn dropdown-item" id="sell" href="?filter=sell">Sell</a>
                        <a class="btn dropdown-item" id="buy" href="?filter=buy">Buy</a>
                        <div class="dropdown-divider"></div>
                        <a class="btn dropdown-item" id="all" href="?filter=all">All transactions</a>
                    </ul>
                </div>
                <div id="admin" class="btn-group ml-3" style="display: none">
                    <a class="btn dropdown-toggle" data-toggle="dropdown">
                        All user actions<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <a class="btn dropdown-item" id="increase" href="?filter=increaseAll">Increase</a>
                        <a class="btn dropdown-item" id="donation" href="?filter=donationAll">Donation</a>
                        <a class="btn dropdown-item" id="sell" href="?filter=sellAll">Sell</a>
                        <a class="btn dropdown-item" id="buy" href="?filter=buyAll">Buy</a>
                        <div class="dropdown-divider"></div>
                        <a class="btn dropdown-item" id="all" href="?filter=actionAll">All transactions</a>
                    </ul>
                </div>
                <div id="usrList" class="btn-group ml-3" style="display: none">
                    <a class="btn dropdown-toggle" data-toggle="dropdown">
                        Filter by users<span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <#list usrList as user>
                            <a class="btn dropdown-item" id="increase" href="?filter=user&param=${user.login}"> ${user.login} : <span style="color: blue">${user.userName}</span> : <span style="color: red">${user.role}</span></a>
                        </#list>
                            <div class="dropdown-divider"></div>
                            <a class="btn dropdown-item" id="all" href="?filter=actionAll">All transactions</a>
                    </ul>
                </div>
            </ul>
            <ul class="navbar-nav">
                <li>
                    <a class="nav-link disabled text-success">${role} : ${userName}!</a>
                </li>
            </ul>
        </div>
    </nav>
    <!------------------------------------------------------------------------------------>

    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Login</th>
            <th scope="col">Operation</th>
            <th scope="col">USD</th>
            <th scope="col">ETH</th>
            <th scope="col">Status</th>
            <th scope="col">Balance USD</th>
            <th scope="col">Balance ETH</th>
            <th scope="col">Date</th>
            <th scope="col">Role</th>
        </tr>
        </thead>
        <tbody>
        <#list transactions as transaction>
            <tr>
                <th>${transaction.id}</th>
                <td>${transaction.login}</td>
                <td>${transaction.operation}</td>
                <td>${transaction.usd}</td>
                <td>${transaction.eth}</td>
                <td>${transaction.status}</td>
                <td>${transaction.balanceUsd}</td>
                <td>${transaction.balanceEth}</td>
                <td>${transaction.date}</td>
                <td>${transaction.role}</td>
            </tr>
            <#else>
            <h3 id="noTransactions" class="ml-5" style="color: green">No transactions...</h3>
        </#list>
        </tbody>
    </table>
</body>
</html>