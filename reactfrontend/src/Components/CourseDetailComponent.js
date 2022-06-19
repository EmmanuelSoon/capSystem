import React, { Component } from 'react';
import { Button, ButtonGroup, Table , Container} from 'reactstrap';
import { Link } from 'react-router-dom';
import {getFirstHiddenTime} from "web-vitals/dist/modules/lib/polyfills/getFirstHiddenTimePolyfill";

class CourseDetail extends Component {

    constructor(props) {
        super(props);
        this.state = {
            batches : [],
            id : this.props.match.params.id
        }
    }
    async componentDidMount() {
        fetch('/admin/coursedetails/' + this.state.id)
            .then(response => response.json())
            .then(data => this.setState({batches: data}));
    }

    render() {
        if (this.state.id == null) {
            return (
                <div>Invalid Access!</div>
            );
        }
        const batches = this.state.batches;

        const batchList = batches.map(batch => {
            const action = (batch) =>{
              if (batch.full === true) {
                  return (
                      <Button color="secondary">Fullly Resgistered</Button>
                  );
              }
              else {
                  return (
                      <Button color="primary" tag={Link} to={"/admin"}>Register new Student</Button>
                  );
              }
            };
            return (
                <tr>
                    <td>{batch.startDate}</td>
                    <td>{batch.endDate}</td>
                    <td>{batch.size} / {batch.maxSize}</td>
                    <td>
                        <ButtonGroup>
                            <Button color="primary" tag={Link} to={"/admin"}>View Batch Details</Button>
                            {action}
                        </ButtonGroup>
                    </td>
                </tr>
            );
        });

        return(
            <Container>
                <div>
                    <h2>Batch Details</h2>
                </div>
                <Table>
                    <thead>
                    <tr>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Current Batch Size</th>
                        <th>Actions</th>
                    </tr></thead>
                    <tbody>
                    {batchList}
                    </tbody>
                </Table>
            </Container>
        );
    }
}
export default CourseDetail;