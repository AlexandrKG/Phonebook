<html>
<head>
    <title>Login and registration</title>
    <link rel="stylesheet" href="/resources/css/jquery-ui.css">
    <link rel="stylesheet" href="/resources/css/style.css">
    <script src="/resources/js/jquery-1.11.1.js" type="text/javascript"></script>
    <script src="/resources/js/jquery-ui.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function() {
            $( "#tabs" ).tabs();
        });
    </script>
    <script type="text/javascript">
        $( "#loginForm" ).submit(function( event ) {
            var $form = $( this ),
                    l_term = $form.find( "input[name='login']" ).val(),
                    p_term = $form.find( "input[name='password']" ).val(),
                    url = $form.attr( "action" );
            var posting = $.post( url, { login: l_term, password: p_term })
                    .done(function(data,status){
                        if(data == "signup") {
                            event.preventDefault();
                            alert("Data: " + data + "\nStatus: " + status);
                        }
            });
        });
        </script>
</head>
<body>
<div class="wrapper">
    <div class="container">
        <div id="tabs">
            <ul>
                <li><a href="#login">Login</a></li>
                <li><a href="#register">Sign up</a></li>
            </ul>
            <div id="login">
                <form method="post" action="newlogin" id="loginForm">
                    <label for="idlogin">Login:</label>
                    <br/>
                    <input type="text" name="login" id="idlogin" pattern="^[a-zA-Z0-9]{3,44}"
                           title="Login should only contain minimum 3 letters"
                    />
                    <br/>
                    <label for="idpassword">Password:</label>
                    <br/>
                    <input type="password" name="password" id="idpassword" pattern="^[a-zA-Z0-9_'^&/+-]{5,44}"
                           title="Password should only contain minimum 5 letters"
                    />
                    <br/>
                    <br/>
                    <input type="submit" value="Login">
                </form>
            </div>

            <div id="register">
                <form method="post" action="newowner" id="registerForm">
                    <label for="rlogin">Login:</label>
                    <br/>
                    <input type="text" name="reglog" id="rlogin" pattern="^[a-zA-Z0-9]{3,44}"
                           title="Login should only contain minimum 3 letters without spetial symbols"
                    />
                    <br/>
                    <label for="rpassword">Password:</label>
                    <br/>
                    <input type="password" name="regpasswd" id="rpassword" pattern="^[a-zA-Z0-9_'^&/+-]{5,44}"
                           title="Password should only contain minimum 5 letters"
                    />
                    <br/>
                    <label for="idfio">Name Surname Patronymic:</label><br/>
                    <input type="text" name="fio" id="idfio" pattern="^[a-zA-Z\s]{5,44}"
                           title="Name Surname Patronymic should only contain minimum 5 letters"
                    />
                    <br/>
                    <br/>
                    <input type="submit" value="Register" name="reguser">
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>