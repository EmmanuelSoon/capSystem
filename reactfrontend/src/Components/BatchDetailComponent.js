import React, { Component } from 'react';
import { Button, ButtonGroup, Table , Container} from 'reactstrap';
import { Link } from 'react-router-dom';
import {getFirstHiddenTime} from "web-vitals/dist/modules/lib/polyfills/getFirstHiddenTimePolyfill";

class BatchDetail extends Component {

    constructor(props) {
        super(props);
        this.state = {
            batch : [],
            id : this.props.match.params.id,
            isLoaded: false
        }
        this.remove = this.remove.bind(this);
    }
    async componentDidMount() {
        fetch('/admin/coursedetails/batch/' + this.state.id)
            .then(res => res.json())
            .then(data => this.setState({batch: data, isLoaded: true}));
    }

    async remove(stdId, name, cdId) {
        await fetch(`/admin/student/course/${name}/${cdId}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        fetch('/admin/coursedetails/batch/' + this.state.id)
            .then(res => res.json())
            .then(data => this.setState({batch: data, isLoaded: true}));
    };

    render() {
        const { batch, isLoaded, id } = this.state
        if (!isLoaded){
            return (
                <div>Loading.....</div>
            );
        }

        const students = batch.student_course;
        const getGPA = ({gpa}) => {
            if (gpa < 0) {
                return (
                    <span>NaN</span>
                );
            }
            else {
                return (
                    <span>parseFloat(gpa).toFixed(1)</span>
                );
            }
        }
        var index = 0;
        const studentList = students.map(student => {
            index++;
            return (
                <tr>
                    <td>{index}</td>
                    <td>{student.id}</td>
                    <td>{student.name}</td>
                    <td>{parseFloat(student.gpa).toFixed(1)}</td>
                    <td>
                        <ButtonGroup>
                            <Button color="danger" tag={Link} onClick={() => this.remove(student.id, student.name, this.state.id)}>Remove Student <span className="fa fa-times"></span></Button>
                        </ButtonGroup>
                    </td>
                </tr>
            );
        });
        const batchSummary = () => {
            return (
              <div className="row mt-4 mb-4">
                  <div className="col-12 col-md-4" >
                      <strong>Course Name : { batch.course.name}</strong>
                  </div>
                  <div className="col-12 col-md-4">
                      <strong>Batch Schedule : { batch.startDate} to { batch.endDate}</strong>
                  </div>
                  <div className="col-12 col-md-4">
                      <strong>No. of Students : { batch.size}/{ batch.maxSize}</strong>
                  </div>
              </div>
            );
        }
        return(
            <Container>
                <div>
                    <h2 className='mt-3'>Student Registration Summary</h2>
                </div>
                <div>
                    {batchSummary()}
                </div>
                <table className='table table-hover text-center mt-3'>
                    <thead className='table-light'>
                    <tr>
                        <th>S/N</th>
                        <th>Student ID</th>
                        <th>Student Name</th>
                        <th>GPA</th>
                        <th>Action</th>
                    </tr></thead>
                    <tbody>
                    {studentList}
                    </tbody>
                </table>
            </Container>
        );
    }
}
export default BatchDetail;