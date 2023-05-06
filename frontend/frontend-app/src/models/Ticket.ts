import { BusRoute } from "./BusRoute"
import { Person } from "./Person"

export interface Ticket{
    id: number,
    payment_method: string,
    seat_number: string,
    personId: number,
    bus_routeId: number,
    
    person?: Person,
    bus_route?: BusRoute,
    
    
    
    
    //noOfTicketsOfBusRouteId: number //DTO

    //"personId": 0,
    //"bus_routeId": 0,

}

