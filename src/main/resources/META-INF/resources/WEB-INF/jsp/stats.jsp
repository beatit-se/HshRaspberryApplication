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
  <div class="container">
    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="furubo">Furubo Now</a>
        </div>
        <ul class="nav navbar-nav">
          <li><a href="furubo">Home</a></li>
          <li class="${todayActive}"><a href="stats?for=today">Today</a></li>
          <li class="${weekActive}"><a href="stats?for=week">This Week</a></li>
          <li class="${monthActive}"><a href="stats?for=month">This Month</a></li>
          <li class="${yearActive}"><a href="stats?for=year">Year</a></li>
          <li class="${statsActive}"><a href="stats?for=all">Stats</a></li>
        </ul>
      </div>
    </nav>
  </div>

  <div class="container">
    <h2>${headline} <small> - ${kwh}kwh</small></h2>
    <table class="table table-striped">
      <thead>
        <tr>
          <th>Event</th>
          <th>Time</th>
          <th>Temp Inside</th>
          <th>Temp Outside</th>
          <th>Watt usage</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>Coldest outside</td>
          <td>${coldestOutWhen}</b></td>
          <td>${coldestOutInTemp}</td>
          <td><b>${coldestOutOutTem}</b></td>
          <td>${coldestOutWattUsage}</td>
        </tr>
        <tr>
          <td>Warmest outside</td>
          <td>${warmestOutWhen}</td>
          <td>${warmestOutInTemp}</td>
          <td><b>${warmestOutOutTem}</b></td>
          <td>${warmestOutWattUsage}</td>
        </tr>
        <tr>
          <td>Coldest inside</td>
          <td>${coldestInWhen}</td>
          <td><b>${coldestInInTemp}</b></td>
          <td>${coldestInOutTem}</td>
          <td>${coldestInWattUsage}</td>
        </tr>
        <tr>
          <td>Warmest inside</td>
          <td>${warmestInWhen}</td>
          <td><b>${warmestInInTemp}</b></td>
          <td>${warmestInOutTem}</td>
          <td>${warmestInWattUsage}</td>
        </tr>
        <tr>
          <td>Highest watt usage</td>
          <td>${highestWattWhen}</td>
          <td>${highestWattInTemp}</td>
          <td>${highestWattOutTem}</td>
          <td><b>${highestWattWattUsage}</b></td>
        </tr>
        <tr>
          <td>Lowest watt usage</td>
          <td>${highestWattWhen}</td>
          <td>${lowestWattInTemp}</td>
          <td>${lowestWattOutTem}</td>
          <td><b>${lowestWattWattUsage}</b></td>
        </tr>
      </tbody>
    </table>
  </div>
</body>
</html>