import React, { Component } from 'react';
import {Button, Card, CardBody, CardTitle, CardText} from 'reactstrap';
import { Link } from 'react-router-dom';

class Home extends Component {


    render(){
        return (
            <div className='col-12 mt-5'>
                <div className='mb-3 d-flex justify-content-center'>
                    <Card>
                        <CardBody>
                            <CardTitle tag="h5">
                                Students
                            </CardTitle>
                            <CardText>
                                For information about students currently enrolled in the system click here!
                            </CardText>
                            <Button size="sm" color="primary" tag={Link} to={"/admin/student"}>Go to Student Page</Button>
                        </CardBody>
                    </Card>
                </div>

                <div className='mb-3 d-flex justify-content-center '>
                    <Card>
                        <CardBody>
                            <CardTitle tag="h5">
                                Lecturers
                            </CardTitle>
                            <CardText>
                                For information about Lecturers currently teaching in the system click here!
                            </CardText>
                            <Button size="sm" color="secondary" tag={Link} to={"/admin/lecturer"}>Go to Lecturer Page</Button>
                        </CardBody>
                    </Card>
                </div>

                <div className='mb-3 d-flex justify-content-center '>
                    <Card>
                        <CardBody>
                            <CardTitle tag="h5">
                                Courses
                            </CardTitle>
                            <CardText>
                                For information about Courses availalbe in the system click here!
                            </CardText>
                            <Button size="sm" color="secondary" tag={Link} to={"/admin/courselist"}>Go to Lecturer Page</Button>
                        </CardBody>
                    </Card>
                </div>

            </div>

        )
    }


}
export default Home;