import React from 'react';
import {connect} from "react-redux";
import * as actionCreators from "../actions/indexAction";
import CarImage from "../component/carImage";

class CarImageCon extends React.Component{
    render(){
        return(
            <CarImage handleClick={this.props.loadCarImages} carImages={this.props.carImages}></CarImage>
        )
    }
}

const mapStateToProps=(state)=>{
    return state
};

export default connect (mapStateToProps, actionCreators)(CarImageCon);