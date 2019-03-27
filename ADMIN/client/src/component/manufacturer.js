import React from "react";
import ReactTable from '../../node_modules/react-table';
import matchSorter from '../../node_modules/match-sorter';


class Manufacturer extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            manufacturers: [],
            columns:[
              {
                Header: "Manufacturer",
                columns: [
                  {
                    Header: 'Id',
                    accessor: 'manufacturerId',
                    maxWidth: 40
                  },
                  {
                    Header: "Name",
                    id: "name",
                    accessor: d => d.name,
                    filterMethod: (filter, rows) =>
                    matchSorter(rows, filter.value, { keys: ["name"] }),
                    filterAll: true
                    // maxwidth: 100

                    //accessor: d => d.models.map((val) => {return val.name})
                    //accessor: d => d.models.map((val) => val.modelYears.map((val2) => {return val2.yearValue}))
                  }
                ]
              }
            ]
        }
      }      
      render() {
        console.log(this.props);
        // console.log(arr.length)
        console.log(this.props.manufacturers.length)
        return (
            <div className="container" style={{ marginTop: 15 }}>
                <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
                <p/>
                {this.props.manufacturers.length > 0 ?
                  <ReactTable
                  data={this.props.manufacturers}
                  filterable
                  defaultFilterMethod={(filter, row) =>
                  String(row[filter.id]) === filter.value}
                  columns={this.state.columns}
                  defaultPageSize = {5}
                  pageSizeOptions = {[5, 10, 15, 20]}
                  className="-striped -highlight"
                  /> : 
                  null}
            </div>
        );
      }
}

export default Manufacturer;