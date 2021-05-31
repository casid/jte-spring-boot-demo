## jte Spring Boot demo

A simple demo of jte running with Spring Boot.

---

### Deploy to Heroku

Requirements: Heroku CLI

- git clone
- cd into project
- heroku apps:create jte-demo --region eu
  - echo "This creates a 'heroku' remote repo"
  - git remote -v show
- git push heroku
- https://jte-demo.herokuapp.com/
- heroku apps:destroy jte-demo

