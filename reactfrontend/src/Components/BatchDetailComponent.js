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
    }
    async componentDidMount() {
        fetch('/admin/coursedetails/batch/' + this.state.id)
            .then(res => res.json())
            .then(data => this.setState({batch: data, isLoaded: true}));
    }

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
                            <Button color="danger" tag={Link} to={"/admin"}>Remove Stduent</Button>
                        </ButtonGroup>
                    </td>
                </tr>
            );
        });
        const batchSummary = () => {
            return (
              <div className="row">
                  <div className="col-12 col-md-4" >
                      <strong>Course Name :{ batch.course.name}</strong>
                  </div>
                  <div className="col-12 col-md-4">
                      <strong>Batch Schedule: { batch.startDate} to { batch.endDate}</strong>
                  </div>
                  <div className="col-12 col-md-4">
                      <strong>Stduent Registered: { batch.size}/{ batch.maxSize}</strong>
                  </div>
              </div>
            );
        }
        return(
            <Container>
                <div>
                    <h2>Stduent Registration Summary</h2>
                </div>
                <div>
                    {batchSummary()}
                </div>
                <Table className="text-center">
                    <thead>
                    <tr>
                        <th>S/N</th>
                        <th>Stduent ID</th>
                        <th>Stduent Name</th>
                        <th>GPA</th>
                        <th>action</th>
                    </tr></thead>
                    <tbody>
                    {studentList}
                    </tbody>
                </Table>
            </Container>
        );
    }
}
export default BatchDetail;