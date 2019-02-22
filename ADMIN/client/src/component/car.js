import React from "react";
import BootstrapTable from '../../node_modules/react-bootstrap-table-next';
import paginationFactory from 'react-bootstrap-table2-paginator';


class Car extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            cars: [],
            columns: [{
              dataField: 'carId',
              text: 'Car ID',
              sort: true
            },
            {
              dataField: 'active',
              text: 'Active',
              sort: true
            }]
          }
    }
      render() {
        console.log(this.props);
        // console.log(arr.length)
        console.log(this.props.cars.length)
        return (
            <div className="container" style={{ marginTop: 50 }}>
                <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
                <p/>
                {this.props.cars.length > 0 ? <BootstrapTable 
                    striped
                    hover
                    keyField='id' 
                    data={ this.props.cars } 
                    columns={ this.state.columns }
                    pagination={ paginationFactory() }/> : null}
            </div>
        );
      }
}

export default Car;