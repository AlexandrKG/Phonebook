<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="/resources/css/jquery-ui.css">
    <link rel="stylesheet" href="/resources/css/style.css">
    <script src="/resources/js/jquery-1.11.1.js" type="text/javascript"></script>
    <script src="/resources/js/jquery-ui.js" type="text/javascript"></script>
</head>
<body>
<div class="wrapper">
    <div class="container">
        <div id="tabs">
            <h2>Sign up, please:</h2>
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