import {
    TableContainer,
    Paper,
    Table,
    TableHead,
    TableRow,
    TableCell,
    TableBody,
    CircularProgress,
    Container,
    IconButton,
    Tooltip,
    TextField,
    Button,
    Box,
  } from "@mui/material";
  
  import EditIcon from "@mui/icons-material/Edit";
  import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
  import { useCallback, useEffect, useState } from "react";
  import { Link, useParams } from "react-router-dom";
  import AddIcon from "@mui/icons-material/Add";
  import { BusRoute } from "../../models/BusRoute";
  import { GlobalURL } from "../../main";
  import { BACKEND_API_URL } from "../../constants";
  import axios from "axios";
import { PersonAverageLugWeightDTO } from "../../models/PersonAverageLugWeightDTO";
  
export const PersonStatistic = () => {
    const [loading, setLoading] = useState(false);
    const [people, setPeople] = useState<PersonAverageLugWeightDTO[]>([]);
    const [page, setPage] = useState(0);
    const [pageSize, setPageSize] = useState(100); //Items per Page
    const [numItems, setNumItems] = useState(0);
    const [hasNextPage, setHasNextPage] = useState(true);
  
    const fetchPeople = useCallback(async (page: number, pageSize: number) => {
      try {
        const dataResponse = await axios.get(
          `${BACKEND_API_URL}/people/average-distance-of-luggage-weight/page/${page}/size/${pageSize}`
        );


        // Fetch data for next page
      const nextPageResponse = await axios.get(
        `${BACKEND_API_URL}/people/average-distance-of-luggage-weight/page/${page + 1}/size/${pageSize}`
      );
      const nextPageData = nextPageResponse.data;
  
        const data = dataResponse.data;
        setNumItems(data.length);
  
        // if (data.length === 0) {
        //   setHasNextPage(false);
        // } 
        if (nextPageData.length === 0) {
          setHasNextPage(false);
        }
        else {
          setHasNextPage(true);
          setPeople(data);
        }
  
        setLoading(false);
      } catch (error) {
        console.error(error);
        setLoading(false);
      }
    }, []);
  
    useEffect(() => {
      setLoading(true);
      fetchPeople(page, pageSize);
    }, [fetchPeople, page, pageSize]);
  
    const handlePreviousPage = () => {
      setPage(Math.max(page - 1, 0));
    };
  
    const handleNextPage = () => {
      if (hasNextPage) {
        setPage(page + 1);
      }
    };
  
    const canNextPage = hasNextPage; //&& numItems === pageSize;
  
    return (
      <Container>
        <h1>Statistic for people</h1>
        {loading && <CircularProgress />}
  
        {!loading && people.length === 0 && <div>No People found</div>}
  
        {!loading && (
          <div style={{ display: "flex", alignItems: "center" }}>
            <Button
              sx={{ color: "black" }}
              disabled={page === 0}
              onClick={handlePreviousPage}
            >
              Previous Page
            </Button>
  
            <Button
              sx={{ color: "black" }}
              onClick={handleNextPage}
              disabled={!canNextPage}
            >
              Next Page
            </Button>
          </div>
        )}
      
      {!loading && people.length > 0 && (
            <TableContainer component={Paper}>
              <Table sx={{ minWidth: 800 }} aria-label="simple table">
                <TableHead>
                  <TableRow>
                    <TableCell>#</TableCell>
                    {/* <TableCell align="center">Person ID</TableCell> */}
                    <TableCell align="center">First Name</TableCell>
                    <TableCell align="center">Last Name</TableCell>
                    <TableCell align="center">Average weight</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {people.map((people: PersonAverageLugWeightDTO, index) => (
                    <TableRow key={people.personId}>
                      <TableCell component="th" scope="row">
                        {index + 1}
                      </TableCell>
                      <TableCell align="center">{people.firstName}</TableCell>
                      <TableCell align="center">{people.lastName}</TableCell>
                      <TableCell align="center">{people.averageWeight}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          )}	
  
      </Container>
    );
  };
  