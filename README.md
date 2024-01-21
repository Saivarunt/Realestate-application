# Realestate-application

## NOTE
In resources/application.properties;
`spring.jpa.hibernate.ddl-auto` is set to `create-drop`
Set `spring.jpa.hibernate.ddl-auto` to `update` if you want to persist changes to DB after each session.

## Admin
Has access to all methods and is the only one who can access and get all user info.

Admin user has been created manually for purpose of demonstration.
Application does not allow users to register as admin.


## User
Default role set to all registering users.

3 registerations available `[AGENT, BUYER, SELLER]` each giving access to respective api's.
Also given ability to add or remove roles from above list.
Can update basic user info and have access to all `UserController` methods.
Added methods to allow users to set and delete their images.

## Agent
Acts as a mediator to buy a property.

## Buyer
Can select an agent to be mediator of a purchase.
Can rate agents based on service.

## Seller
List property info; add, delete, update property information and location.
Added methods to allow users to set and delete images of properties.

#
Added methods to increase price of property based on popularity of property.

Each User has access to specific methods based on their role listed under specific `controller`.