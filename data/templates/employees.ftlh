<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Employees</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;500;600&display=swap" rel="stylesheet">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
            <link rel="stylesheet" href="css/style.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
                crossorigin="anonymous"></script>
</head>

<head>
        <nav class="navbar navbar-expand-md bg-dark mb-5" data-bs-theme="dark">
            <div class="container-xxl">
                <a class="navbar-brand" href="/books"><img class="logo" src="images/logo.png" alt="instroom"></a>
                <button class="navbar-toggler text-light bg-orange btn-user ms-auto" type="button"
                    data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav"
                    aria-expanded="false" aria-label="Toggle navigation">
                    <i class="bi bi-list-nested"></i>
                </button>
                <button type="button" class="btn btn-user bg-orange rounded-1 text-light bg-orange order-md-1 ms-3"
                    data-bs-toggle="modal" data-bs-target="#Modal-form-registration">
                    <i class="bi bi-person-circle"></i>
                </button>

                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav fw-medium mx-auto">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/books">О книгах
                            </a>
                        </li>
                        <li class="mx-3 nav-item">
                            <a class="nav-link " href="/employees">Сотрудники</a>
                        </li>
                        <li class="mx-3 nav-item">
                            <a class="nav-link " href="/journal">Журнал</a>
                        </li>
                    </ul>
                </div>
                <!-- Modal 1 -->
                <div class="modal fade" id="Modal-form-registration" tabindex="-1" aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content bg-light">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5 text-light" id="exampleModalLabel">Авторизация</h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                            </div>
                            <div class="modal-body" id="myModal">
                                <form id="touch">
                                    <div class="mb-3">
                                        <label for="guest" class="form-label">Имя</label>
                                        <input type="text" name="name" class="form-control" placeholder="Aaron Hizzler"
                                            id="guest" autofocus>
                                    </div>
                                    <div class="mb-3">
                                        <label for="guest" class="form-label">Email</label>
                                        <input type="email" name="email" class="form-control"
                                            placeholder="name@example.com" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="inputPassword">Пароль</label>
                                        <input type="password" name="password" class="form-control" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="inputPassword">Повторите пароль</label>
                                        <input type="password" name="repeatPassword" class="form-control" required>
                                    </div>
                                </form>
                            </div>
                            <button form="touch" type="submit" class="btn btn-dark text-light border-0 rounded-0 py-3"
                                >Зарегистрироваться </button>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
    </head>

<body class="bg-secondary">
<div class = "container ">
<table class="table table-dark table-striped">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">First name</th>
        <th scope="col">Last name</th>
        <th scope="col">Reading book</th>
    </tr>
    </thead>
    <tbody>

    <#list employees as employee>
    <tr>
        <th>${employee.id}</th>
        <td><a class="nav-link" href="/info">${employee.firstName}</a></td>
        <td><a class="nav-link" href="/info">${employee.lastName}</a></td>
        <td>${employee.isReading!"on Tik Tok"}</td>
    </tr>
</#list>

</tbody>
</table>
</div>

</body>
<footer class="bg-dark py-5 mt-5">
        <div class="container footer-container ">
            <div class=" opacity-75 mb-2 ">
                <a href="#" class="text-decoration-none text-light">Соглашение пользователя </a>
                <p>«Copyright © Название 2023»</p>
            </div>

            <div class="numbers ms-lg-5 mb-2 ms-md-3 ps-3  ">
                <p>+7 495 120-32-14</p>
                <p>+7 495 120-32-15</p>
            </div>

            <div class="links ms-lg-auto ps-3 ms-md-auto">
                <a href="https://youtu.be/HSt0ZMl4TVQ?si=wrmAXkU2aN_8HxDy"
                    class="btn text-decoration-none text-dark  bg-light"><i class="bi bi-instagram"></i></a>
                <a href="#" class="btn text-decoration-none text-dark  bg-light ms-4"><i class="bi bi-twitter"></i></a>
            </div>
        </div>
    </footer>
</html>