import React, { Component } from 'react';
import { Button, ButtonGroup, Table , Container} from 'reactstrap';
import { Link } from 'react-router-dom';


class StudentCourse extends Component {
    defaultStudent = {
        name: '',
        username: '',
        password: '',
        email: '',
        active: true,
    };

    constructor(props) {
        super(props);

        this.state = {studentCourses: [], student: this.defaultStudent};
        this.remove = this.remove.bind(this);
    }

    async componentDidMount() {
        fetch(`/admin/student/course/${this.props.match.params.id}`)
            .then(response => response.json())
            .then(data => this.setState({studentCourses: data}));

        if(this.state.studentCourses !== null){
            const currentStudent = await (await fetch(`/admin/student/${this.props.match.params.id}`)).json();
            this.setState({student: currentStudent});
        }
      }

      async remove(id) {
        await fetch(`/admin/student/course/${this.state.student.username}/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => this.setState({studentCourses: data}));
    };
    
      
    render() {
        const {studentCourses, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }



        const studentCourseList = studentCourses.map(studentCourseDetail => {
            return (
                <tr key= {studentCourseDetail.courseDetailId}>
                    <td>
                        {studentCourseDetail.courseName}
                    </td>
                    <td>
                        {studentCourseDetail.startDate}
                    </td>
                    <td>
                        {studentCourseDetail.endDate}
                    </td>
                    <td>
                        {studentCourseDetail.gpa}
                    </td>

                    <td>
                        <ButtonGroup>
                            {/* <Button size="sm" color="primary" tag={Link} to={"/admin/student/course" + studentCourseDetail.studentId}>Edit</Button> */}
                            <Button size="sm" color="danger" onClick={() => this.remove(studentCourseDetail.courseDetailId)}>Delete</Button>
                        </ButtonGroup>
                    </td>
                </tr>
            );
        });

        return (
            <Container fluid>
                <div className="float-right">
                    {/* <Button color="success" tag={Link} to={`/admin/student/course/${this.state.student.username}/new`}>Enroll Student into a new Course</Button> */}
                </div>
                <h3>{this.state.student.name}'s Current Enrolment</h3>
                <Table>
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
                                Student GPA
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {studentCourseList}
                    </tbody>
                </Table>
            </Container>
        );

    }
    

}

export default StudentCourse;