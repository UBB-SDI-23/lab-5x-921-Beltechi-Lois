import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";

import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AppMenu } from "./components/AppMenu";
import { AppHome } from "./components/AppHome";
import { BusRouteShowAll } from "./components/busroutes/BusRouteShowAll";
import { BusRouteDetails } from "./components/busroutes/BusRouteDetails";
import { BusRouteDelete } from "./components/busroutes/BusRouteDelete";
import { BusRouteAdd } from "./components/busroutes/BusRouteAdd";
import { BusRouteUpdate } from "./components/busroutes/BusRouteUpdate";
import { BusRouteFilter } from "./components/busroutes/BusRouteFilter";
import { PersonShowAll } from "./components/people/PersonShowAll";
import { PersonStatistic } from "./components/people/PersonStatistic";
import { TicketShowAll } from "./components/ticket/TicketShowAll";
import { LuggageShowAll } from "./components/luggages/LuggageShowAll";
import { LuggageStatistic } from "./components/luggages/LuggageStatistic";
import { LuggageAdd } from "./components/luggages/LuggageAdd";
import { TicketAdd } from "./components/ticket/TicketAdd";
import { PersonAdd } from "./components/people/PersonAdd";
import { LuggageDetails } from "./components/luggages/LuggageDetails";
import { PersonDetails } from "./components/people/PersonDetails";
import { TicketDetails } from "./components/ticket/TicketDetails";
import { LuggageUpdate } from "./components/luggages/LuggageUpdate";
import { PersonUpdate } from "./components/people/PersonUpdate";
import { TicketUpdate } from "./components/ticket/TicketUpdate";
import { PersonDelete } from "./components/people/PersonDelete";
import { TicketDelete } from "./components/ticket/TicketDelete";
import { LuggageDelete } from "./components/luggages/LuggageDelete";



function App() {
	return (
		<React.Fragment>
			<Router>
				<AppMenu />

				<Routes>
					<Route path="/" element={<AppHome />} />
					
					<Route path="/busroutes" element={<BusRouteShowAll />} />
					<Route path="/busroutes/:busRouteId/details" element={<BusRouteDetails/>} />
					<Route path="/busroutes/:busRouteId/delete" element={<BusRouteDelete />} />
					<Route path="/busroutes/add" element={<BusRouteAdd />} />
					<Route path="/busroutes/:busRouteId/edit" element={<BusRouteUpdate />} />
					<Route path="/distance/filterBusRoutes" element={<BusRouteFilter />} />

					<Route path="/people" element={<PersonShowAll />} />
					<Route path="/people/statistic/PeopleAverageLuggageWeight" element={<PersonStatistic />} />
					<Route path="/people/add" element={<PersonAdd />} />
					<Route path="/people/:personId/details" element={<PersonDetails/>} />
					<Route path="/people/:personId/edit" element={<PersonUpdate />} />
					<Route path="/people/:personId/delete" element={<PersonDelete />} />





					<Route path="/tickets" element={<TicketShowAll />} />
					<Route path="/tickets/add" element={<TicketAdd />} />
					<Route path="/tickets/:ticketId/details" element={<TicketDetails/>} />
					<Route path="/tickets/:ticketId/edit" element={<TicketUpdate />} />
					<Route path="/tickets/:ticketId/delete" element={<TicketDelete />} />




					<Route path="/luggages" element={<LuggageShowAll />} />
					<Route path="/luggages/statistic/american-nationality" element={<LuggageStatistic />} />
					<Route path="/luggages/add" element={<LuggageAdd />} />
					<Route path="/luggages/:luggageId/details" element={<LuggageDetails/>} />
					<Route path="/luggages/:luggageId/edit" element={<LuggageUpdate />} />
					<Route path="/luggages/:luggageId/delete" element={<LuggageDelete />} />


					


					


					
				</Routes>
			</Router>
		</React.Fragment>
	);
}

export default App;