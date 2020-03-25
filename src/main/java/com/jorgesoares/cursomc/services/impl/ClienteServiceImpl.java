package com.jorgesoares.cursomc.services.impl;

import com.jorgesoares.cursomc.domain.Cidade;
import com.jorgesoares.cursomc.domain.Cliente;
import com.jorgesoares.cursomc.domain.Endereco;
import com.jorgesoares.cursomc.domain.enums.Perfil;
import com.jorgesoares.cursomc.domain.enums.TipoCliente;
import com.jorgesoares.cursomc.dto.ClienteDTO;
import com.jorgesoares.cursomc.dto.ClienteNewDTO;
import com.jorgesoares.cursomc.repositories.CidadeRepository;
import com.jorgesoares.cursomc.repositories.ClienteRepository;
import com.jorgesoares.cursomc.repositories.EnderecoRepository;
import com.jorgesoares.cursomc.security.UserSS;
import com.jorgesoares.cursomc.services.ClienteService;
import com.jorgesoares.cursomc.services.ImageService;
import com.jorgesoares.cursomc.services.S3Service;
import com.jorgesoares.cursomc.services.UserService;
import com.jorgesoares.cursomc.services.exceptions.AuthorizationException;
import com.jorgesoares.cursomc.services.exceptions.DataIntegrityException;
import com.jorgesoares.cursomc.services.exceptions.ObjNotFoundException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {


    @Autowired
    private ClienteRepository repo;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Value("${img.profile.size}")
    private Integer size;

    @Override
    public Cliente find(Integer id) {

        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    @Override
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    @Override
    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    @Override
    public void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome((obj.getNome()));
        newObj.setEmail(obj.getEmail());
    }

    @Override
    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir uma cliente que possui pedidos e/ou endereco");
        }
    }

    @Override
    public List<Cliente> findAll() {
        return repo.findAll();
    }

    @Override
    public Cliente findByEmail (String email){
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())){
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Cliente> obj = Optional.ofNullable(repo.findByEmail(email));
        return obj.orElseThrow(() -> new ObjNotFoundException(
                "Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName()));
        
    }

    @Override
    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    @Override
    public Cliente fromDTO(ClienteDTO objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
    }

    @Override
    public Cliente fromDTO(ClienteNewDTO objDto) {
        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfCnpj(),
                TipoCliente.toEnum(objDto.getTipo()), bCryptPasswordEncoder.encode(objDto.getSenha()));
        Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
        Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
                objDto.getBairro(), objDto.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());
        if (objDto.getTelefone2() != null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if (objDto.getTelefone3() != null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }
        return cli;
    }

    @Override
    public URI uploadProfilePicture(MultipartFile multipartFile) {
        UserSS user = UserService.authenticated();

        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }
        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);

        String fileName = prefix + user.getId() + ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

    }
}
