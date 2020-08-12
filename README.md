# Diary App

This solution is made by Sebastian Porling and Josefin Salomaa

## Project

This project uses maven for getting dependencies and packaging.
You can import this project and compile/run with maven.

## Execution

You can find this project up on:

https://diary-app-sp-js.herokuapp.com/

or by using your IDE or executing the following while in the root directory:

```bash
java -jar diary_app-1.jar
```

## Motivation and explanation

This project utilizes **H2 SQL** in memory database, 
we use JPA to create the database and tables and use
the **data.sql** file in resources to generate the **Entries** table.
Spring Boot finds the **data.sql** file at the start of execution and executes it.

The 'backend' have three controllers, one repository and one data object/entity.
It is the entity that decides how the table **Entries** will look like.
It has the following properties:

|name|type|property|
|----|----|----|
|id|long|primary|
|date|Date|not null|
|text|String|not null|
|image|String|not null|
|active|boolean|not null default true|

The **WebController** handles the request for the index page.
It loads active users from the **EntryRepository** and adds them as an attribute.

The **EntryController** is a RestController that has the following methods:

|name|method|return|comment|
|----|----|----|----|
|/entries|GET|{data, message}|Gets all entries and puts them into data, if no entries data is set to null|
|/entry/create|POST|{data, message}|Creates an entry, data will be equal to the new entry if success|
|/entry/update|PATCH|{data, message}|Update an entry, data equals modified entry on success|
|/entry/delete|DELETE|{null, message}|Changes entry to inactive, data always null|

It gives back a ResponseEntity with the following info:

|name|type|comment|
|----|----|----|
|data|Object|Here we can put all the entries or null|
|message|String|Here we can put a message about the request|

It also provides a HttpStatus, for explaining when it is successful or not.

Then we have the **BasicErrorController** that redirects the user
to an appropriate error page like 404 or 500.

The class **EntryRepository** has all the methods to query the database.

For the 'frontend' we generate the index page with thymeleaf the first time it is loaded.
All the actions happens through javascript after that.
The file **fetch_api.js** does most of the work.
It generates a new table when a search happens, modifies the row locally and on the database.
Adds a new row to the table and the database. Most through jQuery Ajax requests.
So everything happens asynchronously which gives a more smooth experience.
We have event listeners for the search field, search button, and forms.
The forms are in the **add modal** and **edit modal**. A modal is a fancy popup through bootstrap.