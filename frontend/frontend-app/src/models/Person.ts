import { Passenger } from "./Passenger";

export interface Person{
    id: number,
    firstName: string,
    lastName: string,
    dateOfBirth: string,
    gender: string,
    phoneNumber: string,
    

    //passengerId: number,  //#statistic...  ???  DTO
    //numBusRoutesTaken: number //#statistic   ???  DTO

    //passenger: Passenger   // A person is a passenger
}