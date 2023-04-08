import { BusRoute } from "./BusRoute"
import { Person } from "./Person"

export interface Ticket{
    id: number,
    person: Person[],
    bus_route: BusRoute[],
    payment_method: string,
    seat_number: string

    //"personId": 0,
    //"bus_routeId": 0,

}

