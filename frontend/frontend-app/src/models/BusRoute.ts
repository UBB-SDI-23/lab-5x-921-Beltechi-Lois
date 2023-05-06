import { Person } from "./Person"

export interface BusRoute{
    id: number,
    bus_name: string,
    route_type: string,
    departure_hour: string,
    arrival_hour: string,
    distance: string
    noOfTicketsOfBusRouteId: number  //DTO
    people: Person[]
    
}



