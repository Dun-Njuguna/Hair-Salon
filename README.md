# Hair-Salon
### CREATED BY  Duncan Kariuki

----------------------------------------------------------------------

## Project Objectives
To create an application that allows the owner of a salon be able to add clients to stylists and delete their entries.

----------------------------------------------------------------------

## Technologies used
This project has been written using java,spark framework,Postgres SQL - Database, css and html.


-----------------------------------------------------------------------------

## Behaviour driven Development (BDD)
|#User inputs   |  #Example outputs |         
|---------------|-------------------|
|User adds a stylist        |stylist is added |
|User adds a client to a stylist        | client is assigned a stylist  |
|User updates client's information       | client's information is updated  |
|User deletes a client's information      | client's information is deleted  |
|User updates stylist's information       |  stylist's information is updated  |
|deletes a stylist's information       |  stylist's information is deleted  |
---------------------------------------------------------------------------------

## Setup/Installation Requirements
To use the application
  1. Clone the repository.
  2. Navigate to the folder.
  3. To re-create the database, use the following steps;
  4. Open folder with terminal.
  5. Run PSQL:(if you don't have postgress install it first).
  6. CREATE DATABASE hair_salon;
  7. CREATE TABLE clients (id serial PRIMARY KEY, name varchar, stylistId int);
  8. CREATE TABLE stylists (id serial PRIMARY KEY, name varchar);
  9. Open with the text editor(sublime prefered) of your choice(optional step)
  10. Run "gradle build" to install dependencies.
  11. Run "gradle run" then open your browser.

---------------------------------------------------------------------

## Known bugs
As of witting, there are no Known bugs.

## Support or contribution instructions
If you require to make any changes to this project, then reach out to me on the email below.

## Contact information
For any questions or suggestions please feel free to reach out to me at duncankariuki60@gmail.com

-----------------------------------------------------------------------------

## Copyright and license information

MIT License

Copyright (c) [2019] [Duncan Kariuki]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.