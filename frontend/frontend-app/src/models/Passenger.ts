import { Luggage } from "./Luggage"

export interface Passenger{
    id: number,
    timesTravelled: string,
    firstName: string,
    lastName: string,
    dateOfBirth: string,
    gender: string,
    phoneNumber: string
    luggages: Luggage[]      // can have many luggages 
}