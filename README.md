# hotel-room-management
Distributed client server app using Java RMI that manages rooms from a particular hotel. Java RMI (Remote Method Invocation) facilitates communication between Java programs running on different computers over a network. It's a powerful tool for building distributed applications, enabling seamless interaction between client and server components.

### Running the App
---
``` bash
make 
java HRServer # On different window
java HRClient # On different window
```

The Application runs using the terminal, the `HRClient` class that contains the Client's functions and different parameters for the actions that we want.

#### Booking a Room
```
java HRClient book A 13 Kostas
```

#### Show available rooms ordered by type
```
java HRClient list
```

#### Show a list of customers that have a reservation 
```
java HRClient guests
```

#### Removing a Booking
```
java HRClient cancel A 13 Kostas
```



### Examples of running the App
---
#### Inserting 3 reservations and display of list & guests
<img src=https://github.com/ConSpd/hotel-room-management/assets/74179715/67aed3f1-787e-446a-8a2d-64f10b74e19f width = 400>

#### Inserting a reservation at same name & room
<img src=https://github.com/ConSpd/hotel-room-management/assets/74179715/65b94e4b-3afb-4d0f-9748-6916ae1a748d width = 400>

> <sub>No new record was created but Mike's previous record was updated, increasing the number of reservations he had.</sub>

#### Deleting a reservation
<img src=https://github.com/ConSpd/hotel-room-management/assets/74179715/71c87d1d-d761-400d-b8c7-fce96ee97909 width=400>

#### Booking more rooms than available exception
<img src=https://github.com/ConSpd/hotel-room-management/assets/74179715/7f937271-d90a-4feb-87cf-7146bc5a1e74 width=400>

#### Waiting queue feature
<img src=https://github.com/ConSpd/hotel-room-management/assets/74179715/15a93964-efc5-4b9a-accb-c5f73e609cb9 width=400><br>
<img src=https://github.com/ConSpd/hotel-room-management/assets/74179715/5e664d29-491f-4f30-9822-381d8499b67f width=400><br>
<img src=https://github.com/ConSpd/hotel-room-management/assets/74179715/eaaa4a3d-a485-4422-9977-22e826bfd189 width=400><br>
<img src=https://github.com/ConSpd/hotel-room-management/assets/74179715/473f2931-dd42-4df5-aec4-e4b61a90d4ab width=400><br>
<img src=https://github.com/ConSpd/hotel-room-management/assets/74179715/885b359e-632e-41a0-b0ca-7d4c467779fc width=400><br>









