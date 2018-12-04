using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace ACE
{
    public partial class MainPage : ContentPage
    {
        public MainPage()
        {
            InitializeComponent();

            var onboarded = false;

            if (onboarded == false){
                onboarded = true;
                Navigation.PushAsync(new Language());
            }
        }
        public void SearchBttn(object sender, EventArgs args)
        {
            Navigation.PushAsync(new SearchPage());
        }
        public void ScanBttn(object sender, EventArgs args)
        {

        }
    }
}

