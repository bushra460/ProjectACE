import React from 'react';
import {connect} from "react-redux";
import * as actionCreators from "../actions/indexAction";
import Car from "../component/car";

class CarCon extends React.Component{
    render(){
        return(
            <Car handleClick={this.props.loadCars} cars={this.props.cars}></Car>
        )
    }
}

const mapStateToProps=(state)=>{
    return state
};

export default connect (mapStateToProps, actionCreators)(CarCon);