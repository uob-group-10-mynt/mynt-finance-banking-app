import Footer from './Footer';
import Header from './Header';

// TODO: got up too here 

function layout({ children }){
    return(
        <div>
            <Header></Header>
            <main>
                hello world
                {children}
            </main> 
            <Footer></Footer>
        </div>
    );
}

export default layout;