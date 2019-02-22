import React from "react";
import BootstrapTable from '../../node_modules/react-bootstrap-table-next';
import paginationFactory from 'react-bootstrap-table2-paginator';


class CarImage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            carImages: [],
            columns: [{
              dataField: 'carImageId',
              text: 'Car Image ID',
              sort: true
            },
            {
              dataField: 'uri',
              text: 'URL',
              sort: true
            },
            {
              dataField: 'exteriorImage',
              text: 'Exterior Image',
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
        console.log(this.props.carImages.length)
        return (
            <div className="container" style={{ marginTop: 50 }}>
                <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
                <p/>
                {this.props.carImages.length > 0 ? <BootstrapTable 
                    striped
                    hover
                    keyField='id' 
                    data={ this.props.carImages } 
                    columns={ this.state.columns }
                    pagination={ paginationFactory() }/> : null}
            </div>
        );
      }
}

export default CarImage;