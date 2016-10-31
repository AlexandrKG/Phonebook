<%@ page import="com.springboot.domain.User" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html>
    <head>
        <link rel="stylesheet" href="/resources/css/jquery-ui.css">
        <link rel="stylesheet" href="/resources/css/style.css">
        <link rel="stylesheet" href="/resources/css/modal.css">
        <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/js/modal.js" type="text/javascript"></script>
    </head>
    <body>
        <h1>Phonelist</h1>
        <h2>Welcome <c:out value="${loguser.fio}"/></h2>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#loadgrd").click(function(){
                    resiveAllData("${loguser.id}");
                });
                $("#loadfilter").click(function(){
                    resiveFilterData();
                });
                $("#delcontacts").click(function(){
                    delRecords();
                    resiveFilterData();
                });
            });
        </script>
        <!-- <br><div><button id="loadgrd">Load all contacts</button></div><br><br> -->
        <div>
            <form method="post" action="getfiltrphonelist" id="filter">
                <table>
                    <tr>
                        <td><label for="firstname">Name:</label></td>
                        <td> <input type="text" name="fname" id="firstname"/></td>
                    </tr>
                    <tr>
                        <td><label for="idSecondname">Surname:</label></td>
                        <td><input type="text" name="fsurname" id="idSecondname"/></td>
                    </tr>
                    <tr>
                        <td><label for="idMobile">Mobail :</label></td>
                        <td><input type="text" name="fmobile" id="idMobile"/></td>
                    </tr>
                </table>
                <input type="hidden" name = "uid" value="${loguser.id}">
            </form>
            <button id="loadfilter">Load contacts</button>
        </div>
        <br>
        <div><button id="delcontacts">Del contacts</button></div>
        <br>
        <div><button id="addphone">Add new contact</button></div>
        <br>

        <table id="plist">
            <thead>
            <tr id="listhead">
                <th></th>
                <th>Name</th>
                <th>Surname</th>
                <th>Patronymic</th>
                <th>Mobail</th>
                <th>Phone</th>
                <th>Address</th>
                <th>Email</th>
            </tr>
            </thead>
            <tbody id = "tblist"></tbody>
        </table>

        <div id="modal_form">
            <span id="modal_close">X</span>
            <form method="post" action="newphone" id="phoneForm">
                <h2>Add new contact</h2>
                <label for="idname">Name:</label><br/>
                <input type="text" name="name" id="idname" pattern="^[a-zA-Z]{4,44}$"
                       title="Name should only contain minimum 4 letters"/>
                <br/>
                <label for="idsurname">Surname:</label><br/>
                <input type="text" name="surname" id="idsurname" pattern="^[a-zA-Z]{4,44}$"
                       title="Surname should only contain minimum 4 letters"/>
                <br/>
                <label for="idmiddlename">Patronymic:</label><br/>
                <input type="text" name="middlename" id="idmiddlename" pattern="^[a-zA-Z]{4,44}$"
                       title="Patronymic should only contain minimum 4 letters"/>
                <br/>
                <label for="idmob">mobile phone:</label><br/>
                <input type="text" name="mobile" id="idmob" pattern="^[\+380]{4}[\(]\d{2}[\)]\d{7}$"
                       title="number pattern is +380(xx)xxxxxxx"/>
                <br/>
                <label for="idhome">home phone:</label><br/>
                <input type="text" name="phone" id="idhome" pattern="^[\+380]{4}[\(]\d{2}[\)]\d{7}$"
                       title="number pattern is +380(xx)xxxxxxx"/>
                <br/>
                <label for="idaddr">address:</label><br/>
                <input type="text" name="address" id="idaddr"/>
                <br/>
                <label for="idemail">Email:</label><br/>
                <input type="text" name="email" id="idemail" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
                       title="e-mail"/>
            </form>
            <div>
                <button id="modal_test">Add</button>
            </div>
        </div>
        <div id="overlay"></div>
        <script type="text/javascript">
            $("#modal_test").click(function() {
                sendData("${loguser.id}");
                closemodal();
            });
        </script>

    </body>
</html>