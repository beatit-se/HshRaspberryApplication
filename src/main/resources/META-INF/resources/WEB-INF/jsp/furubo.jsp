<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html lang="en">
<head>
    <!--  Static resources form Webjars -->
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="/webjars/jquery/1.11.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Furubo Today</title>
</head>

<body>


<!--
    <div class="jumbotron">
      <h1>Furubo Today <small>secondary</small></h1>
      <p>This is some text. Hello ${name}</p>
    </div>
-->

    <div class="container">
    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="furubo">Furubo Now</a>
        </div>
        <ul class="nav navbar-nav">
          <li class="active"><a href="furubo">Home</a></li>
          <li><a href="stats?for=today">Today</a></li>
          <li><a href="stats?for=week">This Week</a></li>
          <li><a href="stats?for=month">This Month</a></li>
          <li><a href="stats?for=year">Year</a></li>
          <li><a href="stats?for=all">Stats</a></li>
        </ul>
      </div>
    </nav>
      <div class="page-header">
        <h1>Right now</h1>
      </div>

<div class="container-fluid bg-1 text-center">
<div class="row">
<!-- <div class="col-sm-1"><h2>In temp:</h2></div> -->
<div class="col-sm-2"><h1><span class="label label-success">${currentInTemp} C</span></h1></div>
</div>
<div class="row">
<!-- <div class="col-sm-1"><h2>Out temp:</h2></div> -->
<div class="col-sm-2"><h1><span class="label label-info">${currentOutTemp} C</span></h1></div>
</div>
<div class="row">
<!-- <div class="col-sm-1"><h2>Watt usage:</h2></div> -->
<div class="col-sm-2"><h1><span class="label label-danger">${currentWattUsage} W</span></h1></div>
</div>
</div>

</div>

<div class="container">
<br/><br/>
Info:
<div class="well">
<span class="label label-success">Inside temperature</span>
<span class="label label-info">Outside temperature</span>
<span class="label label-danger">Watt usage</span>
</div>

<!--
        <div class="row">
          <div class="col-sm-4">Temperature in</div>
          <div class="col-sm-8">20</div>
        </div>
        <div class="row">
          <div class="col-sm-4">Temperature out</div>
          <div class="col-sm-8">4</div>
        </div>

        <small>Some small lighter text</small>

    -->
</div>

</body>
</html>