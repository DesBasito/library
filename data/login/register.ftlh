<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Registration form</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@400;500;600&display=swap" rel="stylesheet">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
                crossorigin="anonymous"></script>
    <link rel="stylesheet" href="../css/forms.css">
    <link rel="stylesheet" href="../css/style.css">
</head>

<body>
<head>
        <nav class="navbar navbar-expand-md bg-dark mb-5" data-bs-theme="dark">
            <div class="container-xxl">
                <a class="navbar-brand" href="/books"><img class="logo" src="images/logo.png" alt="instroom"></a>
                <button class="navbar-toggler text-light bg-orange btn-user ms-auto" type="button"
                    data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav"
                    aria-expanded="false" aria-label="Toggle navigation">
                    <i class="bi bi-list-nested"></i>
                </button>
                <button type="button" class="btn btn-link btn-user bg-orange rounded-1 text-light bg-orange order-md-1 ms-3"
                                               onclick="window.location.href='/login'"
                                                   data-bs-toggle="tooltip" data-bs-placement="left" title="Authorisation">
                                                   <i class="bi bi-person-circle"></i>
                                               </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav fw-medium mx-auto">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/books">О книгах
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
            </div>
        </nav>
    </head>
    <main>
        <form action="/register" method="post">
            <fieldset>
                <div class="legend">
                    <p>Registration</p>
                    <img src="../images/1.jpg" alt="coins" style="display:block; width: 200px; height: 200px;">
                </div>
               <div class="form-element">
                                   <label for="guest">Имя</label>
                                   <input type="text" name="firstName" required placeholder="Имя" autofocus>
                               </div>
                               <div class="form-element">
                                   <label for="guest">фамилия</label>
                                   <input type="text" name="lastName" required placeholder="Фамилия">
                               </div>
                               <div class="form-element">
                                   <label for="guest">Дата рождения</label>
                                   <input type="date" name="birthdate" required placeholder="Дата рождения">
                               </div>
                               <div class="form-element">
                                   <label for="guest">номер телефона</label>
                                   <input type="number" name="phone" placeholder="номер телефона (не обезятельно)"
                                       id="guest">
                               </div>
                                <div class="form-element">
                                                   <label for="guest">Профессия</label>
                                                   <input type="text" name="position" placeholder="Профессия (не обязательно)">
                                               </div>
                                               <div class="form-element">
                                                   <input  list="datalistOptions" name="hobby"  placeholder="Хобби (не обезятельно)">
                                                   <datalist id="datalistOptions">
                                                     <option value="Reading">Reading</option>
                                                     <option value="Writing">Writing</option>
                                                     <option value="Painting">Painting</option>
                                                     <option value="Drawing">Drawing</option>
                                                     <option value="Cooking">Cooking</option>
                                                     <option value="Gardening">Gardening</option>
                                                     <option value="Photography">Photography</option>
                                                     <option value="Hiking">Hiking</option>
                                                     <option value="Running">Running</option>
                                                     <option value="Swimming">Swimming</option>
                                                     <option value="Cycling">Cycling</option>
                                                     <option value="Skiing">Skiing</option>
                                                     <option value="Knitting">Knitting</option>
                                                     <option value="Woodworking">Woodworking</option>
                                                     <option value="Playing an Instrument">Playing an Instrument</option>
                                                     <option value="Dancing">Dancing</option>
                                                     <option value="Sculpting">Sculpting</option>
                                                     <option value="Fishing">Fishing</option>
                                                     <option value="Traveling">Traveling</option>
                                                     <option value="Collecting stamps">Collecting stamps</option>
                                                   </datalist>
                                               </div>
                <div class="form-element">
                    <label for="user-email">email</label>
                    <input type="email" name="email" id="user-email" placeholder="your email" required autofocus>
                </div>
                <div class="form-element">
                    <label for="password">password</label>
                    <input type="password" name="user-password" id="user-password" placeholder="your password" required>
                </div>
                <div class="hr-line">
                    <span class="details">one more step to go</span>
                </div>
                <div class="form-element">
                    <button class="register-button" type="submit">Register!</button>
                </div>
            </fieldset>
        </form>
    </main>
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