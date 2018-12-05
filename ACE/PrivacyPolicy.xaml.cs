using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace ACE
{
    public partial class PrivacyPolicy : ContentPage
    {
        public PrivacyPolicy()
        {
            InitializeComponent();
        }
        public void AcceptClicked(object sender, EventArgs args)
        {
            Navigation.PopToRootAsync(true);
        }
        public void DeclineClicked(object sender, EventArgs args)
        {
            Navigation.PopToRootAsync();
        }
    }
}
