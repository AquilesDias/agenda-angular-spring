import { Component, OnInit } from '@angular/core';
import { ContatoService } from '../contato.service';
import { Contato } from './contato';
import { ContatoDetalheComponent} from '../contato-detalhe/contato-detalhe.component'

import { MatDialog} from '@angular/material/dialog'
import { PageEvent } from '@angular/material/paginator' 

import {  FormBuilder, FormGroup, Validators } from '@angular/forms'


@Component({
  selector: 'app-contato',
  templateUrl: './contato.component.html',
  styleUrls: ['./contato.component.css']
})

export class ContatoComponent implements OnInit {

  formulario : FormGroup
  contatos : Contato[] = [];
  colunas : ['foto', 'id', 'nome', 'email', 'favorito'];
  
  totalElementos = 0;
  pagina = 0;
  tamanho = 10;
  pageSizeOptions: number[] = [10];

  constructor(
    private service : ContatoService,
    private fb : FormBuilder,
    private dialog: MatDialog
  ) {}     

  ngOnInit(): void {
     this.montarFormulario();
     this.listarContatos(this.pagina, this.tamanho); 
  }

  montarFormulario(){
    this.formulario = this.fb.group({
      nome:['', Validators.required],
      email:['', [Validators.required,Validators.email]]
    });
  }


  favoritar(contato : Contato){
    this.service.favorite(contato).subscribe(response => {
      contato.favorito = !contato.favorito;
    });
  }

  listarContatos(pagina=1, tamanho=1){
    this.service.list(pagina, tamanho).subscribe(response => {
      this.contatos = response.content;
      this.totalElementos = response.totalElements;
      this.pagina = response.number;
    });
  }

  submit(){
     
    const formValue = this.formulario.value;
    const contato : Contato = new Contato(formValue.nome, formValue.email);
    
    this.service.save(contato).subscribe( resposta =>{
      let lista : Contato[] = [...this.contatos, resposta];
      this.contatos = lista;
    });
  }
 
  uploadFoto(event, contato){
    const files = event.target.files;
    if(files){
      const foto = files[0];
      const formData : FormData = new FormData();

      formData.append("foto", foto);

      this.service.uploud(contato, formData)
                  .subscribe( response => this.listarContatos());
    } 
  } 

  visualizarContato( contato:Contato){
      this.dialog.open(ContatoDetalheComponent,{        
          width:'350px',
          height:'421px',
          data: contato
      });
    }

    paginar( event: PageEvent){
      this.pagina = event.pageIndex;
      this.listarContatos(this.pagina, this.tamanho)
    }



}
