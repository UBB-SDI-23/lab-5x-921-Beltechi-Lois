import { Luggage } from "./Luggage"

export interface Passenger{
    id: number,
    timesTravelled: string,
    firstName: string,
    lastName: string,
    dateOfBirth: string,
    gender: string,
    phoneNumber: string
    //luggage: Luggage[]      // can have many luggages 
}