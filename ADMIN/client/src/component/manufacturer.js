import React from "react";
import BootstrapTable from '../../node_modules/react-bootstrap-table-next';
import filterFactory, { textFilter } from '../../node_modules/react-bootstrap-table2-filter';
import paginationFactory from 'react-bootstrap-table2-paginator';


class Manufacturer extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            manufacturers: [],
            columns: [{
              dataField: 'manufacturerId',
              text: 'Manufacturer ID',
              sort: true
            },
            {
              dataField: 'name',
              text: 'Manufacturer Name',
              sort: true,
              filter: textFilter()
            }]
          }
    }
      render() {
        console.log(this.props);
        // console.log(arr.length)
        console.log(this.props.manufacturers.length)
        return (
            <div className="container" style={{ marginTop: 50 }}>
                <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
                <p/>
                {this.props.manufacturers.length > 0 ? <BootstrapTable 
                    striped
                    hover
                    keyField='id' 
                    data={ this.props.manufacturers } 
                    columns={ this.state.columns } 
                    filter={ filterFactory() }
                    pagination={ paginationFactory() }/> : null}
            </div>
        );
      }
}

export default Manufacturer;