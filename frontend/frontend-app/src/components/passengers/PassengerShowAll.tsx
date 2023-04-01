import { useEffect, useState } from "react";
import { Passenger } from "../../models/Passenger";

export const PassengerShowAll = () => {
    //const [count, setCount] = useState(0);
  
    const [passengers, setPassengers] = useState([]);

    useEffect(() =>{   
    fetch("http://localhost:8080/passengers")
        .then((res) => res.json())
        .then((data) => {
                console.log(data);    
                setPassengers(data);
                    
                });
    }, []);  
    
    if (passengers.length === 0){
        return <div>No passengers.</div>;
    }

    return (    
      <div className="App">
        <h1>Passengers list</h1>
        <table>
            <tr>
                <th>#</th>
                <th>timesTravelled</th>
                <th>firstName</th>
                <th>lastName</th>
                <th>dateOfBirth</th>
                <th>gender</th>
                <th>phoneNumber</th>
            </tr>
            {passengers.map((passenger: Passenger, index) => (
                <tr key={index}>
                    <td>{index}</td>
                    <td>{passenger.timesTravelled}</td>
                    <td>{passenger.firstName}</td>
                    <td>{passenger.lastName}</td>
                    <td>{passenger.dateOfBirth}</td>
                    <td>{passenger.gender}</td>
                    <td>{passenger.phoneNumber}</td>
                </tr>    
            ))}
        </table>
        
      </div>
    );
  }
  
  