import React, { Component } from 'react';
import { Button, ButtonGroup, Table, Container } from 'reactstrap';
import { Link } from 'react-router-dom';


class Enrolment extends Component {
    defaultStudent = {
        name: '',
        username: '',
        password: '',
        email: '',
        active: true,
    };

    constructor(props) {
        super(props);

        this.state = { availableCourses: [], student: this.defaultStudent };
        this.add = this.add.bind(this);
    }

    async componentDidMount() {
        fetch(`/admin/student/course/${this.props.match.params.id}/new`)
            .then(response => response.json())
            .then(data => this.setState({ availableCourses: data }));

        if (this.state.studentCourses !== null) {
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
            .then(data => this.setState({ availableCourses: data }));
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
            <Container fluid>
                <h3>Available Courses for {this.state.student.name}</h3>
                <Table className='mb-5'>
                    <thead>
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
                        </tr>
                    </thead>
                    <tbody>
                        {availableCourseList}
                    </tbody>
                </Table>
                {/* <Button outline color="success" tag={Link} to={`/admin/student/course/${this.state.student.username}/new`}>Enroll Student into a new Course</Button> */}
            </Container>
        );

    }


}

export default Enrolment;