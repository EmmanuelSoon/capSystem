import React, { Component } from 'react';
import { Button, ButtonGroup, Table , Container} from 'reactstrap';
import { Link } from 'react-router-dom';
import {getFirstHiddenTime} from "web-vitals/dist/modules/lib/polyfills/getFirstHiddenTimePolyfill";

class CourseDetail extends Component {

    constructor(props) {
        super(props);
        this.state = {
            batches : [],
            id : this.props.match.params.id,
            isLoaded: false
        }
        this.deleteCourseDetail = this.deleteCourseDetail.bind(this);
    }
    async componentDidMount() {
        fetch('/admin/coursedetails/' + this.state.id)
            .then(response => response.json())
            .then(data => this.setState({batches: data, isLoaded: true}));
    }

    async deleteCourseDetail(id) {
        await fetch(`/admin/course/batch/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        fetch('/admin/coursedetails/' + this.state.id)
            .then(response => response.json())
            .then(data => this.setState({batches: data, isLoaded: true}));
    };


    render() {
        if (this.state.id == null) {
            return (
                <div>Invalid Access!</div>
            );
        }
        if (!this.state.isLoaded) {
            return <p>Loading...</p>;
        }
        if (this.state.batches.length === 0) {
            return(
                <Container className="mt-5">
                <h2><Link to={"/admin/courselist"}><span className="fa fa-arrow-circle-left" aria-hidden="true"></span></Link>
                    There is currently no batches for this Course.
                </h2>
                <div>
                    <Button color="success" tag={Link} to={"/admin/course/coursedetail/new/" + this.state.id}>Click to add New Batch Now !</Button>
                </div>
                </Container>
            );
        }

        const batches = this.state.batches;

        const action = (batch) =>{
            if (batch.full === true) {
                return (
                    <div>
                        <Button size='sm' color="danger">Full</Button>
                    </div>
                );
            }
            else {
                return (
                    <div>
                        <Button size='sm' color="warning">Not Full</Button>
                    </div>
                );
            }
        };

        const deleteaction = (batch) => {
            const now = new Date();
            if (now < new Date(batch.startDate)) {
                return (
                    <div>
                    <Button size="sm" color="danger" onClick={() => this.deleteCourseDetail(batch.id)}>Remove Batch <span className="fa fa-times"></span></Button>
                    </div>
                );
            }
            else if (now < new Date(batch.endDate)) {
                return (
                    <div>
                        <Button size="sm" color="success">Ongoing <span className="fa fa-commenting-o"></span> </Button>
                    </div>
                );
            }
            else {
                return (
                    <div>
                        <Button size="sm" color="secondary">Completed <span className="fa fa-ban"></span></Button>
                    </div>
                );
            }
        }

        const batchList = batches.map(batch => {
            return (
                <tr key={batch.id}>
                    <td>{batch.startDate}</td>
                    <td>{batch.endDate}</td>
                    <td>{batch.size} / {batch.maxSize}</td>
                    <td>
                        {action(batch)}
                    </td>
                    <td>
                        <div className='col-12'>
                            {deleteaction(batch)}
                        </div>
                    </td>
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"/admin/coursedetails/batch/" + batch.id}>View Batch Details</Button>
                        </ButtonGroup>
                    </td>
                </tr>
            );
        });


        return(
            <Container className="mt-5">
                <div className="float-end">
                    <Button color="success" tag={Link} to={"/admin/course/coursedetail/new/" + this.state.id}>Add New Batch</Button>
                </div>
                <div>
                    <h2><Link to={"/admin/courselist"}><span className="fa fa-arrow-circle-left" aria-hidden="true"></span></Link> Batch List ({batches[0].course.name})</h2>
                </div>
                <table className='table table-hover text-center mt-3'>
                    <thead className='table-light'>
                    <tr>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Current Batch Size</th>
                        <th>Status</th>
                        <th>Actions</th>
                        <th>View Details</th>
                    </tr></thead>
                    <tbody>
                    {batchList}
                    </tbody>
                </table>
            </Container>
        );
    }
}
export default CourseDetail;