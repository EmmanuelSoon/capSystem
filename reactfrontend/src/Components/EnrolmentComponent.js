import React, { Component } from 'react';
import { Button, ButtonGroup, Table, Container } from 'reactstrap';
import { Link } from 'react-router-dom';
import { FormErrors } from './FormErrors';



class Enrolment extends Component {
    defaultStudent = {
        studentId: 0,
        name: '',
        username: '',
        password: '',
        email: '',
        active: true,
    };

    constructor(props) {
        super(props);

        this.state = { 
            availableCourses: [], 
            student: this.defaultStudent,
            formErrors: {Error:''},
         };
        this.add = this.add.bind(this);
    }

    async componentDidMount() {
        fetch(`/admin/student/course/${this.props.match.params.id}/new`)
            .then(response => response.json())
            .then(data => this.setState({ availableCourses: data }));

        if (this.state.availableCourses !== null) {
            const currentStudent = await (await fetch(`/admin/student/${this.props.match.params.id}`)).json();
            this.setState({ student: currentStudent });
        }
    }

    async add(id) {
        await fetch(`/admin/student/course/${this.state.student.username}/${id}`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            

        })
            .then(response => response.json())
            .then(data => {
                if(data.message !== 'Item couldnt be added'){
                    this.setState({ availableCourses: data, formErrors: {Error: ""} })
                }
                else{
                    this.setState({formErrors: {Error: ": Sorry... unable to enroll as selected course is full"}})

                }
                
            
            });
    };


    render() {
        const { availableCourses, isLoading } = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }



        const availableCourseList = availableCourses.map(CourseDetail => {
            return (
                <tr key={CourseDetail.id}>
                    <td>
                        {CourseDetail.course.name}
                    </td>
                    <td>
                        {CourseDetail.startDate}
                    </td>
                    <td>
                        {CourseDetail.endDate}
                    </td>
                    <td>
                        {CourseDetail.size}/{CourseDetail.maxSize}
                    </td>
                    <td>
                        <ButtonGroup>
                            {/* <Button size="sm" color="primary" tag={Link} to={"/admin/student/course" + studentCourseDetail.studentId}>Edit</Button> */}
                            <Button outline size="sm" color="primary" onClick={() => this.add(CourseDetail.id)}>Enroll Student</Button>
                        </ButtonGroup>
                    </td>
                </tr>
            );
        });

        return (
            <Container className='mt-5'>
                <h3><Link to={"/admin/student/course/" + this.state.student.studentId}><span className="fa fa-arrow-circle-left" aria-hidden="true"></span></Link> Available Courses for {this.state.student.name}</h3>
                <div className="panel panel-default">
                        <FormErrors formErrors={this.state.formErrors} />
                    </div>
                <table className='table table-hover text-center'>
                        <thead className='table-light'>
                        <tr>
                            <th>
                                Course Name
                            </th>
                            <th>
                                Course Start Date
                            </th>
                            <th>
                                Course End Date
                            </th>
                            <th>
                                Current Enrolment
                            </th>
                            <th>

                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {availableCourseList}
                    </tbody>
                </table>
                {/* <Button outline color="success" tag={Link} to={`/admin/student/course/${this.state.student.username}/new`}>Enroll Student into a new Course</Button> */}
            </Container>
        );

    }


}

export default Enrolment;