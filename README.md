[![Build Status](https://travis-ci.org/Rasm-P/Sem3Exam.svg?branch=master)](https://travis-ci.org/Rasm-P/Sem3Exam)

Travis fails almost every time due to a "Bad Gateway" issue. Might have something to do with a dependency that it can't find. But it cleans, builds and deploys successfully, and everything works.

3. Semester, Exam
 
API:
=====================
Back end deployed at: https://barfodpraetorius.dk/Sem3Exam

Front end deployed at https://teamonesem3exam.surge.sh

Open API: https://barfodpraetorius.dk/Sem3Exam/openapi/

Group: Team One
=======================
Lukas Bjørnvad

Rasmus Prætorius
https://github.com/Rasm-P

Simon Norup

Henning Wiberg

Group Contract
==================
Respect your group members and their work. Try to avoid working on features that other members are currently working on.

If you want to change other members' work, contact them and coordinate.

Whenever you have made a small feature, do the following:

- Clean n Build

- If build/tests fail, fix issues and clean n build again

- Git add + git commit (with descriptive message)

- Git pull to see if anyone else has pushed, fix possible merge conflicts

- If any changes occured; begin from point 1 again

- Git push

- Wait for travis -- if fail; drop everything and fix immediately

- Tell other members to pull.

- Always wait for other members to finish pushing and for travis to check it before pushing a feature yourself.


Instructions
==================
Preconditions:
In order to use this code, you should have a local developer setup + a "matching" droplet on Digital Ocean as described in the 3. semester guidelines^

To set up the project backend to work on your machine and pipeline, change the following:
- pom.xml : Domain name
- config.properties : names of both databases
- .travis.yaml : name of test database
- Travis, environment variables: REMOTE_USER + REMOTE_PW
- rest, @OpenAPIDefinition: Local and remote server url for openapi.
- CorsResponseFilter, Access-Control-Allow-Origin: Your frontend deployment
- if you want user functionality: run the createUserRoles.sql script on your non-test database

For instructions on how to use the API see the following openapi decription:
https://barfodpraetorius.dk/Sem3Exam/openapi/

^ This project contains two major documentation files: 
 - [First time users - getting started](README_proof_of_concept.md)
 - [How to use for future projects](README_how_to_use.md)
