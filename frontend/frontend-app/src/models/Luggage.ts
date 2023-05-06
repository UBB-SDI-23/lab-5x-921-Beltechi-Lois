import { Person } from "./Person";

export interface Luggage{
    id: number,
    type: string,
    weight: number,
    color: string,
    priority: string,
    status: string,
    description: string,
    personId: number,
    person?: Person

}