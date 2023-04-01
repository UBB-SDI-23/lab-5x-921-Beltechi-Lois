import { Passenger } from "./Passenger";

export interface Luggage{
    id: number,
    busNumber: number,
    weight: number,
    size: number,
    owner: string,
    status: string,
    //passenger_Id: number   ???? DTO
    
    //passenger: Passenger  // only one unique passenger can have this

}