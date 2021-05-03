# AsteroidRadar

-Asteroid Radar App retrieve the latest asteroids data(like the distance from earth,PotentiallyHazardous or not ,etc...) from Nasa API using retrofit + picasso and Cache the

response inside local database (Room) the data are presented into recycler view according to user preferences using overflow 

menu(like selecting to show only asteroids for the upcoming 7 days only), all recycler items are clickable ,and upon clicking the user will be transferred to

asteroid detail screen, all asteroids are sorted by date using sql query with room.

- the asteroid list screen contains an image view represent Nasa Image of the week which is retrieved from a different Nasa endpoint other than the asteroids List

- it downloads and saves today's asteroids in background once a day when the device is charging and wifi is enabled using work manager.

-the app delete asteroids from the previous day once a day using the same workManager that downloads the asteroids.

