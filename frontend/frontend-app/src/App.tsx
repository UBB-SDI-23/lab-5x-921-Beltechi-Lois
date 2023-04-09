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
					
				</Routes>
			</Router>
		</React.Fragment>
	);
}

export default App;