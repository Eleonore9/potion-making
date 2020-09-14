# POTION-MAKING
## Programming language resources


[![Build Status](https://travis-ci.org/Eleonore9/potion-making.svg?branch=master)](https://travis-ci.org/Eleonore9/potion-making)


### Minimal Clojure API deployed to Heroku: https://potion-making.herokuapp.com/

### Used as a backend for https://eleonore9.github.io/potion-frontend/

You can try:

* to get all info:

`~$ GET http://potion-making.herokuapp.com/all-data`

`~$ GET http://potion-making.herokuapp.com/all-names`

`~$ GET http://potion-making.herokuapp.com/all-paradigms`

`~$ GET http://potion-making.herokuapp.com/all-types`

`~$ GET http://potion-making.herokuapp.com/all-uses`


* To get info by type:

`~$ GET http://potion-making.herokuapp.com/name?input=Python`

`~$ GET http://potion-making.herokuapp.com/paradigm?input=Imperative`

`~$ GET http://potion-making.herokuapp.com/type?input=static`

`~$ GET http://potion-making.herokuapp.com/use?input=web`



________________________

Built on top of Heroku's [clojure-getting-started](https://github.com/heroku/clojure-getting-started) app using Compojure and Ring.

For more information about using Clojure on Heroku, see these Dev Center articles:

- [Clojure on Heroku](https://devcenter.heroku.com/categories/clojure)



-------------

<a rel="license" href="http://creativecommons.org/licenses/by-nc/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc/4.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc/4.0/">Creative Commons Attribution-NonCommercial 4.0 International License</a>.

