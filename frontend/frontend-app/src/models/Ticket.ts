import { BusRoute } from "./BusRoute"
import { Person } from "./Person"

export interface Ticket{
    id: number,
    person: Person[],
    bus_route: BusRoute[],
    purchase_date: string,
    seat_number: string

}

