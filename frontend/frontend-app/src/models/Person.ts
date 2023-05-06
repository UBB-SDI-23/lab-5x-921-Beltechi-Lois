import { Luggage } from "./Luggage"

export interface Person{
    id: number,
    firstName: string,
    lastName: string,
    nationality: string,
    gender: string,
    phoneNumber: string,
    noOfLuggages: number // DTO
    luggages?: Luggage[]  // luggage used until now....



}