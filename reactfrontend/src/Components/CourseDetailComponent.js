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

        const action = (batch) =>{
            if (batch.full === true) {
                return (
                    <div>
                        <Button color="secondary">Fullly Resgistered</Button></div>
                );
            }
            else {
                return (
                    <div>
                        <Button color="success" tag={Link} to={"/admin"}>Register Stduent</Button></div>
                );
            }
        };

        const batchList = batches.map(batch => {
            return (
                <tr>
                    <td>{batch.startDate}</td>
                    <td>{batch.endDate}</td>
                    <td>{batch.size} / {batch.maxSize}</td>
                    <td>
                        <ButtonGroup>
                            <Button color="primary" tag={Link} to={"/admin/coursedetails/batch/" + batch.id}>View Batch Status</Button>
                            {action(batch)}
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
                <Table className="text-center">
                    <thead >
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