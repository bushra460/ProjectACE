import React from 'react';
import ReactDOM from 'react-dom';
import {createStore, applyMiddleware} from 'redux';
import {Provider} from 'react-redux';
import thunk from 'redux-thunk';
import reducers from './reducers/indexReducer';
import ControlTabs from './component/controlTabs';

import {Jumbotron, Container,Row,Col,Image} from 'react-bootstrap';

import '../node_modules/react-table/react-table.css'
import './index.css'

let store = createStore(reducers, applyMiddleware(thunk))

class App extends React.Component{
    render(){
        return(
            <div>
                <Jumbotron fluid>
                    <Container>
                        <Row>
                            <Col xs={6} md={4}>
                                <Image style={{width: '200px', height: '200px'}} src={require('./images/Badge_of_the_Canada_Border_Services_Agency.png')} thumbnail/>
                            </Col>
                            <Col>
                                <h1>ACE Admin</h1>
                                <p>Click on a tab to view data related to ACE</p>
                            </Col>
                        </Row>
                    </Container>
                </Jumbotron>
                <div className="container">
                    <ControlTabs/>
                </div>
            </div>                    
        )
    }
}

ReactDOM.render(<Provider store={store}><App /></Provider>, document.getElementById('root'));